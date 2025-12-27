import { Container, Card, Text, Group, Title, Grid, Button } from '@mantine/core';
import { useTranslation } from 'react-i18next';

export const TrainingPrograms: React.FC = () => {
    const { t } = useTranslation();

    return (
        <Container size="lg" py="xs">
            <Title order={1} fw="bold" mb="md">
                {t('homePage.trainingPrograms.title')}
            </Title>
            <Grid>
                <Grid.Col span={{ base: 12, md: 4 }}>
                    <Card radius="lg" bg="dark.5" h="100%">
                        <Group gap="sm">
                            <Text fz="h3">
                                {t('homePage.trainingPrograms.cards.jobSearch.title')}
                            </Text>
                        </Group>
                        <Text size="sm" mb="lg" mt="xs">
                            {t('homePage.trainingPrograms.cards.jobSearch.description')}
                        </Text>
                        <Button radius="lg" color="white" w="fit-content">
                            <Text size="sm">
                                {t('homePage.trainingPrograms.cards.jobSearch.button')}
                            </Text>
                        </Button>
                    </Card>
                </Grid.Col>
                <Grid.Col span={{ base: 12, md: 4 }}>
                    <Card radius="lg" bg="dark.5" h="100%">
                        <Group gap="sm">
                            <Text fz="h3">
                                {t('homePage.trainingPrograms.cards.freelance.title')}
                            </Text>
                        </Group>
                        <Text size="sm" mb="lg" mt="xs">
                            {t('homePage.trainingPrograms.cards.freelance.description')}
                        </Text>
                        <Button radius="lg" color="white" w="fit-content">
                            <Text size="sm">
                                {t('homePage.trainingPrograms.cards.freelance.button')}
                            </Text>
                        </Button>
                    </Card>
                </Grid.Col>
                <Grid.Col span={{ base: 12, md: 4 }}>
                    <Card radius="lg" bg="dark.5" h="100%">
                        <Group gap="sm">
                            <Text fz="h3">
                                {t('homePage.trainingPrograms.cards.foreignJobs.title')}
                            </Text>
                        </Group>
                        <Text size="sm" mb="lg" mt="xs">
                            {t('homePage.trainingPrograms.cards.foreignJobs.description')}
                        </Text>
                        <Button radius="lg" color="white" w="fit-content">
                            <Text size="sm">
                                {t('homePage.trainingPrograms.cards.foreignJobs.button')}
                            </Text>
                        </Button>
                    </Card>
                </Grid.Col>
            </Grid>
        </Container>
    );
};
