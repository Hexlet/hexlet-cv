import { StudyPrograms, type TProps } from "@widgets/admin-study-programs"
import { AdminLayout } from "../components/AdminLayout"

const Index = ({ programs }: TProps) => {
    return <StudyPrograms programs={programs} />
}

Index.layout = (page: React.ReactNode) => <AdminLayout>{page}</AdminLayout>

export default Index
