import { KnowledgeBase, type TProps } from "@widgets/knowledge-base"

const Index = ({ articles }: TProps) => {
    return <KnowledgeBase articles={articles} />
}

export default Index