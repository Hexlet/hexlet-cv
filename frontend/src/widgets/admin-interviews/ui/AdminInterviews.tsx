import { Anchor, Title, Table, Checkbox, Button, TextInput, Group, Container } from '@mantine/core'
import { useTranslation } from 'react-i18next'
import { useForm } from '@inertiajs/react'

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

export const AdminInterviews: React.FC<TProps> = (props): JSX.Element => {
    const { interviews } = props
    const { t } = useTranslation()

    const form = useForm({ search: '' })

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        if (!form.data.search.trim()) {
            return
        }
        // form.post('/ru/admin/interview')
        form.setData('search', '')
    }

    return (
        <Container size='xl' py='md'>
            <Title order={2} mb='md' fw={500}>
                {t('adminPage.interviews.title')}
            </Title>
            <form onSubmit={handleSubmit}>
                <Group gap='xs' mb='md'>
                    <TextInput
                        placeholder={t('adminPage.interviews.input')}
                        value={form.data.search}
                        w={240}
                        onChange={e => form.setData('search', e.target.value)}
                    />
                    <Button type='submit' variant='default'>{t('adminPage.interviews.button')}</Button>
                </Group>
            </form>
            <Table withTableBorder verticalSpacing='md'>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th fz='md' py='xs' w='27%'>{t('adminPage.interviews.interviewTitle')}</Table.Th>
                        <Table.Th fz='md' py='xs' w='27%'>{t('adminPage.interviews.interviewSpeaker')}</Table.Th>
                        <Table.Th fz='md' py='xs' w='31%'>{t('adminPage.interviews.interviewVideo')}</Table.Th>
                        <Table.Th fz='md' py='xs' w='15%' ta='center'>{t('adminPage.interviews.interviewPublished')}</Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>
                    {interviews?.map((interview) => (
                        <Table.Tr key={interview.id}>
                            <Table.Td>
                                <Anchor href='' underline='not-hover'>
                                    {interview.title}
                                </Anchor>
                            </Table.Td>
                            <Table.Td>
                                <TextInput value={interview.speaker} readOnly size='xs' w={200} style={{ pointerEvents: 'none' }}/>
                            </Table.Td>
                            <Table.Td>
                                <TextInput value={interview.videoUrl} readOnly size='xs' w={200} style={{ pointerEvents: 'none' }}/>
                            </Table.Td>
                            <Table.Td ta='center'>
                                { /* Пока не знаю, как должны функционировать чекбоксы и какую информацию они должны отправлять */ }
                                <Checkbox checked={interview.isPublished} style={{ display: 'inline-block' }} size='xs' readOnly/>
                            </Table.Td>
                        </Table.Tr>
                    ))}
                </Table.Tbody>
            </Table>
        </Container>
    )
}