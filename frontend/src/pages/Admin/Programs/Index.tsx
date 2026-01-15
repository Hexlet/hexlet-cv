import { StudyPrograms } from "@widgets/study-programs"

// Программы для демонстрации
const mockPrograms = [
    { id: 1, name: 'Frontend-разработчик', duration: 6, lessons: 48, isPublished: true },
    { id: 2, name: 'QA-инженер', duration: 4, lessons: 32, isPublished: false }
]

const Index2 = () => {
    return <StudyPrograms programs={mockPrograms} />
}

export default Index2
