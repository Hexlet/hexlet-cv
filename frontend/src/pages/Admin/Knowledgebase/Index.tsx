import { KnowledgeBase, type TProps } from "@widgets/knowledge-base"
import { AdminLayout } from "../components/AdminLayout"

const Index = ({ articles }: TProps) => {
    return <KnowledgeBase articles={articles} />
}

Index.layout = (page: React.ReactNode) => <AdminLayout>{page}</AdminLayout>

export default Index