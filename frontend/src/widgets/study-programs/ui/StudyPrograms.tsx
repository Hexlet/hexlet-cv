import { Title, Table, Checkbox, Button, TextInput, Group, Container, NumberInput } from '@mantine/core'
import { useTranslation } from 'react-i18next'

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

export const StudyPrograms: React.FC<TProps> = (props): JSX.Element => {
    const { programs } = props
    const { t } = useTranslation()

    return (
        <Container size='xl' py='md'>
            <Title order={2} mb='md' fw={500}>
                {t('adminPage.studyPrograms.title')}
            </Title>
            <Group gap='xs' mb='md'>
                { /* Пока не знаю, что должна делать эта кнопка */ }
                <Button type='submit' variant='default'>{t('adminPage.studyPrograms.button')}</Button>
            </Group>
            <Table withTableBorder verticalSpacing='md'>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th fz='md' py='xs' w='30%'>{t('adminPage.studyPrograms.programName')}</Table.Th>
                        <Table.Th fz='md' py='xs' w='30%'>{t('adminPage.studyPrograms.programDuration')}</Table.Th>
                        <Table.Th fz='md' py='xs' w='15%' ta='center'>{t('adminPage.studyPrograms.programLessons')}</Table.Th>
                        <Table.Th fz='md' py='xs' w='25%' ta='center'>{t('adminPage.studyPrograms.programPublished')}</Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>
                    {programs?.map((program) => (
                        <Table.Tr key={program.id}>
                            <Table.Td>
                                <TextInput
                                    value={program.name}
                                    readOnly
                                    size='xs'
                                    w={500}
                                    />
                            </Table.Td>
                            <Table.Td>
                                <TextInput
                                    value={`${program.duration} мес`} 
                                    readOnly
                                    size='xs'
                                    w={150}
                                    />
                            </Table.Td>
                            <Table.Td align='center'>
                                { /* Пока не знаю, как должны функционировать стрелочки */ }
                                <NumberInput
                                    value={program.lessons}
                                    size='xs'
                                    w={100}
                                    allowNegative={false}
                                    />
                            </Table.Td>
                            <Table.Td ta='center'>
                                { /* Пока не знаю, как должны функционировать чекбоксы и какую информацию они должны отправлять */ }
                                <Checkbox checked={program.isPublished} style={{ display: 'inline-block' }} size='xs' readOnly/>
                            </Table.Td>
                        </Table.Tr>
                    ))}
                </Table.Tbody>
            </Table>
        </Container>
    ) 
}