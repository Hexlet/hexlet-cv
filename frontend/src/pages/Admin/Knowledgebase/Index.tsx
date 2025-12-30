import { KnowledgeBase } from "@widgets/knowledge-base"

// Записи для демонстрации
const mockArticles = [
    { id: 1, title: 'FAQ по платформе', category: 'Общая', isPublished: true },
    { id: 2, title: 'Глоссарий терминов', category: 'Справка', isPublished: false }
]

const Index = () => {
    return <KnowledgeBase articles={mockArticles}/>
}

export default Index