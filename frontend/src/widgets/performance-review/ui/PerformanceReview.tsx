import { Container, Card, Text, Group, Title, Grid, Button } from '@mantine/core';
import { useTranslation } from 'react-i18next';

export const PerformanceReview = () => {
    const { t } = useTranslation();

    return (
        <Container size="lg" py="xs">
            <Title order={1} fw="bold" mb="md">
                {t('homePage.performanceReview.title')}
            </Title>
            <Grid>
                <Grid.Col span={{ base: 12, md: 4 }}>
                    <Card radius="lg" bg="dark.5" h="100%">
                        <Group gap="sm">
                            <Text fz="h3">
                                {t('homePage.performanceReview.cards.skillsTesting.title')}
                            </Text>
                        </Group>
                        <Text size="sm" mb="lg" mt="xs">
                            {t('homePage.performanceReview.cards.skillsTesting.description')}
                        </Text>
                        <Button radius="lg" color="white" w="fit-content">
                            <Text size="sm">
                                {t('homePage.performanceReview.cards.skillsTesting.button')}
                            </Text>
                        </Button>
                    </Card>
                </Grid.Col>
                <Grid.Col span={{ base: 12, md: 4 }}>
                    <Card radius="lg" bg="dark.5" h="100%">
                        <Group gap="sm">
                            <Text fz="h3">
                                {t('homePage.performanceReview.cards.performanceReview.title')}
                            </Text>
                        </Group>
                        <Text size="sm" mb="lg" mt="xs">
                            {t('homePage.performanceReview.cards.performanceReview.description')}
                        </Text>
                        <Button radius="lg" color="white" w="fit-content">
                            <Text size="sm">
                                {t('homePage.performanceReview.cards.performanceReview.button')}
                            </Text>
                        </Button>
                    </Card>
                </Grid.Col>
                <Grid.Col span={{ base: 12, md: 4 }}>
                    <Card radius="lg" bg="dark.5" h="100%">
                        <Group gap="sm">
                            <Text fz="h3">
                                {t('homePage.performanceReview.cards.gradeMarket.title')}
                            </Text>
                        </Group>
                        <Text size="sm" mb="lg" mt="xs">
                            {t('homePage.performanceReview.cards.gradeMarket.description')}
                        </Text>
                        <Button radius="lg" color="white" w="fit-content">
                            <Text size="sm">
                                {t('homePage.performanceReview.cards.gradeMarket.button')}
                            </Text>
                        </Button>
                    </Card>
                </Grid.Col>
            </Grid>
        </Container>
    );
};
