import { StudyPrograms } from "@widgets/study-programs"

type StudyProgramsEntry = {
    id: number
    name: string
    duration: number
    lessons: number
    isPublished: boolean
}

type TProps = {
    programs: StudyProgramsEntry[]
}

const Index = ({ programs }: TProps) => {
    return <StudyPrograms programs={programs} />
}

export default Index
