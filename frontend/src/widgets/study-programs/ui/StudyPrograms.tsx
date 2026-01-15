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

    // стили прописаны временно для разработки
    const inputStyles = {
        input: { 
            color: 'black', 
            backgroundColor: 'white', 
            borderColor: '#ced4da' 
        }
    }

    return (
        <Container size='xl' py='md' bg='white'>
            <Title order={2} mb='md' fw={500} c='black'>
                {t('adminPage.studyPrograms.title')}
            </Title>
            <Group gap='xs' mb='md'>
                { /* Пока не знаю, что должна делать эта кнопка */ }
                <Button type='submit' variant='default'>{t('adminPage.studyPrograms.button')}</Button>
            </Group>
            <Table withTableBorder verticalSpacing='md'>
                <Table.Thead bg='gray.1'>
                    <Table.Tr c='black'>
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
                                    styles={inputStyles}/>
                            </Table.Td>
                            <Table.Td>
                                <TextInput
                                    value={`${program.duration} мес`} 
                                    readOnly
                                    size='xs'
                                    w={150}
                                    styles={inputStyles}/>
                            </Table.Td>
                            <Table.Td align='center'>
                                { /* Пока не знаю, как должны функционировать стрелочки */ }
                                <NumberInput
                                    value={program.lessons}
                                    size='xs'
                                    w={100}
                                    allowNegative={false}
                                    styles={{...inputStyles,
                                        control: {
                                            color: 'black',
                                            borderColor: '#ced4da'
                                        }
                                    }}/>
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