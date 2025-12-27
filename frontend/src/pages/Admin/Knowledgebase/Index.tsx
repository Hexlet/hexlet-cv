import { Title, Table, Checkbox, Button, TextInput, Group, Container, Anchor } from '@mantine/core'
import { useForm } from '@inertiajs/react'
import { useTranslation } from 'react-i18next'

// Примерное описание типа записи в базе знаний
type KBArticle = {
  id: number
  title: string
  category: string
  isPublished: boolean
}

// Примерное описание типа пропсов
type Props = {
    articles: KBArticle[]
}

// Записи для демонстрации
const mockProps = {
    articles: [
        { id: 1, title: 'FAQ по платформе', category: 'Общая', isPublished: true },
        { id: 2, title: 'Глоссарий терминов', category: 'Справка', isPublished: false }
    ]
}

const KnowledgeBase = (props: Props = mockProps): JSX.Element => {
    const { articles } = mockProps // props
    const { t } = useTranslation()

    const form = useForm({ title: '' })

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        if (!form.data.title.trim()) {
            return
        }
        console.log(e)
        // form.post('/ru/admin/knowledgebase')
        form.setData('title', '')
    }

    return (
        <Container size='xl' py='md' bg='white'>
            <Title order={2} mb='md' fw={500} c='black'>
                {t('adminPage.knowledgeBase.title')}
            </Title>
            <form onSubmit={handleSubmit}>
                <Group gap='xs' mb='md'>
                    <TextInput
                        placeholder={t('adminPage.knowledgeBase.input')}
                        value={form.data.title}
                        w={240}
                        onChange={e => form.setData('title', e.target.value)}
                    />
                    <Button type='submit' variant='default'>{t('adminPage.knowledgeBase.button')}</Button>
                </Group>
            </form>
            <Table withTableBorder verticalSpacing='md'>
                <Table.Thead bg='gray.1'>
                    <Table.Tr>
                        <Table.Th fz='md' py='xs' w='35%'>{t('adminPage.knowledgeBase.articleTitle')}</Table.Th>
                        <Table.Th fz='md' py='xs' w='40%'>{t('adminPage.knowledgeBase.articlecategory')}</Table.Th>
                        <Table.Th fz='md' py='xs' w='25%' ta='center'>{t('adminPage.knowledgeBase.articlePublished')}</Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>
                    {articles?.map((article) => (
                        <Table.Tr key={article.id}>
                            <Table.Td>
                                <Anchor href='' c='black' underline='not-hover'>
                                    {article.title}
                                </Anchor>
                            </Table.Td>
                            <Table.Td>
                                <TextInput value={article.category} readOnly size='xs' w={200}/>
                            </Table.Td>
                            <Table.Td ta='center'>
                                { /* Пока не знаю, как должны функционировать чекбоксы и какую информацию они должны отправлять */ }
                                <Checkbox checked={article.isPublished} style={{ display: 'inline-block' }} size='xs' readOnly/>
                            </Table.Td>
                        </Table.Tr>
                    ))}
                </Table.Tbody>
            </Table>
        </Container>
    ) 
}

export default KnowledgeBase