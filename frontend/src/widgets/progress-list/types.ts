import type { IProgress } from '@entities/learning-progress'

export interface IPagination {
  currentPage: number
  totalPages: number
  totalElements: number
  pageSize: number
}

export interface IProgressResponse {
  progress: IProgress[]
  pagination: IPagination
}
