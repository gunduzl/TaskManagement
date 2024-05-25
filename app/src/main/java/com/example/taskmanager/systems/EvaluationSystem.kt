package com.example.taskmanager.systems

import com.example.taskmanager.profileComponents.out.Employee
import com.example.taskmanager.profileComponents.out.Manager
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Staff
import com.example.taskmanager.profileComponents.out.Task
import com.example.taskmanager.profileComponents.out.TaskDifficulty

class EvaluationSystem {

    suspend fun sendNotification(employee: Employee) {
        // Sending notification logic
        println("Notification sent to ${employee.name} at ${employee.email}")
    }

    suspend fun makeRecommendation(employee: Employee) {
        // Making recommendation logic
        println("Recommendation made for ${employee.name}")
    }

    fun evaluateTaskPoint(taskDifficulty: TaskDifficulty): Int {
        val taskPoints = when (taskDifficulty) {
            TaskDifficulty.LOW -> 5
            TaskDifficulty.MEDIUM -> 10
            TaskDifficulty.HIGH -> 15
        }
        return taskPoints
    }

    // Time'a gÃ¶re updatelenecek
    fun evaluatePointFromTask(staff: Staff,task: Task) {

        staff.pointsList.add(task.taskPoint)
    }


    fun evaluateMonthlyStaffPoint(staff:Staff){
        val staffsPoints = staff.pointsList
        var monthlyPoint = 0
        for(point in staffsPoints){
            monthlyPoint += point
        }
        staff.staffPoint = monthlyPoint
    }


    suspend fun evaluateManagerPoint(manager: Manager){
        var managerPoint = 0
        val managerAndStaff = Repository().getStaffsOfManager(manager.id)
        val staffs = managerAndStaff?.staff
        if (staffs != null) {
            for (staff in staffs) {
                managerPoint += staff.staffPoint
            }
            managerPoint /= staffs.size

        }
        manager.managerPoint = managerPoint

    }

}