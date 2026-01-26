import { AdminInterviews } from "@widgets/admin-interviews"
import type { TProps } from "@widgets/admin-interviews/ui/AdminInterviews"

const Index = ({ interviews }: TProps) => {
    return <AdminInterviews interviews={interviews}/>
}

export default Index