export interface IProgress {
  id: number
  programTitle: string
  lastLessonTitle: string
  completedLessons: number
  totalLessons: number
  progressPercentage: number
  isCompleted: boolean
  startedAt: string
  lastActivityAt: string
}
