import { Container, Title, SimpleGrid, Card, Text, Button, alpha } from '@mantine/core'
import { useTranslation } from 'react-i18next'

export const KnowledgeBaseAndInterviews: React.FC = (): JSX.Element => {
    const { t } = useTranslation()

    // Карточки для демонстрации
    const cards = [
        {
            title: t('homePage.knowledgeBaseAndInterview.knowledgeBaseTitle'),
            description: t('homePage.knowledgeBaseAndInterview.knowledgeBaseDescription'),
        },
        {
            title: t('homePage.knowledgeBaseAndInterview.interviewQuestionsTitle'),
            description: t('homePage.knowledgeBaseAndInterview.interviewQuestionsDescription'),
        },
        {
            title: t('homePage.knowledgeBaseAndInterview.realInterviewVideosTitle'),
            description: t('homePage.knowledgeBaseAndInterview.realInterviewVideosDescription'),
      },
    ]

    return (
        <Container size='lg' py='xl'>
            <Title order={1} c='white' mb='md'>{t('homePage.knowledgeBaseAndInterview.title')}</Title>
            <SimpleGrid cols={{ base: 1, sm: 2, lg: 3 }} spacing="lg" verticalSpacing="lg">
                {cards.map((card, index) => (
                    <Card
                        key={index}
                        radius='lg'
                        bg={alpha('#787878', 0.2)}
                        bd={'1px solid ' + alpha('#FFFFFF', 0.1)}
                        >
                        <Text fw={600} fz='xl' c='white' mb='sm'>{card.title}</Text>
                        <Text fz='sm' c='white' mb='md'>{card.description}</Text>
                        <Button 
                            variant='white'
                            color='black'
                            radius='md'
                            w='fit-content'
                        >
                            {t('homePage.knowledgeBaseAndInterview.button')}
                        </Button>
                    </Card>
                ))}
            </SimpleGrid>
        </Container>
    )
}