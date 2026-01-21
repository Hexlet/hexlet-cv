import { AdminInterviews } from "@widgets/admin-interviews"

type InterviewsEntry = {
    id: number
    title: string
    speaker: string
    videoUrl: string
    isPublished: boolean
}

type TProps = {
    interviews: InterviewsEntry[]
}

const Index = ({ interviews }: TProps) => {
    return <AdminInterviews interviews={interviews}/>
}

export default Index