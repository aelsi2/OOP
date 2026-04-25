package e2e

task("Task_1_1_1") {
    name = "1.1.1"
    description = "Heap sort"
    hardDeadline = "2025-09-13"
}

task("Task_1_1_2") {
    name = "1.1.2"
    description = "Console blackjack"
    softDeadline = "2025-09-20"
    hardDeadline = "2025-09-27"
}

task("Task_1_1_3") {
    name = "1.1.3"
    description = "Equations"
    softDeadline = "2025-10-04"
    hardDeadline = "2025-10-11"
}

task("Task_1_2_1") {
    name = "1.2.1"
    description = "Graph"
    softDeadline = "2025-10-18"
    hardDeadline = "2025-11-01"
}

task("Task_1_2_2") {
    name = "1.2.2"
    description = "Hash table"
    softDeadline = "2025-11-08"
    hardDeadline = "2025-11-15"
}

grades {
    date "2025-10-15" name "Mid-semester"
    date "2025-12-27" name "Final"
}