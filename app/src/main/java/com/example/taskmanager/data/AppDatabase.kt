

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanager.data.dao.DbDAO
import com.example.taskmanager.data.entities.CTO
import com.example.taskmanager.data.entities.CTOWithDepartments
import com.example.taskmanager.data.entities.Department
import com.example.taskmanager.data.entities.DepartmentManager
import com.example.taskmanager.data.entities.DepartmentManagerWithStaff
import com.example.taskmanager.data.entities.DepartmentWithDetails
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.data.entities.TaskStaffCrossRef
import com.example.taskmanager.data.entities.TaskWithStaff

@Database(
    entities = [
        CTO::class, Department::class, DepartmentManager::class, Staff::class,
        Task::class, TaskStaffCrossRef::class, DepartmentWithDetails::class,
        DepartmentManagerWithStaff::class, CTOWithDepartments::class, TaskWithStaff::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dbDAO(): DbDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "DATABASE"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}