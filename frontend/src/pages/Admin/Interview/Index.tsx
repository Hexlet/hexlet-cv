import { AdminInterviews, type TProps } from "@widgets/admin-interviews"
import { AdminLayout } from "../components/AdminLayout"

const Index = ({ interviews }: TProps) => {
    return <AdminInterviews interviews={interviews}/>
}

Index.layout = (page: React.ReactNode) => <AdminLayout>{page}</AdminLayout>

export default Index