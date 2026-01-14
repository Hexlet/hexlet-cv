import {
    Container,
    Title,
    Text,
    Card,
    Group,
    Button,
    Stack,
    Divider,
    SimpleGrid,
} from '@mantine/core'
import { useTranslation } from 'react-i18next'

type PricingPlan = {
    id: number
    name: string
    price: string
    features: string[]
    isPopular?: boolean
    discount?: string
}

type PricingProps = {
    pricingPlans?: PricingPlan[]
}

export const Pricing: React.FC<PricingProps> = () => {
    const { t } = useTranslation()

    // Получаем данные из переводов
    const pricingData = {
        title: t('homePage.pricing.title'),
        subtitle: t('homePage.pricing.subtitle'),
        plans: {
            month1: {
                name: t('homePage.pricing.plans.month1.name'),
                price: t('homePage.pricing.plans.month1.price'),
                features: t('homePage.pricing.plans.month1.features', { returnObjects: true }),
            },
            month3: {
                name: t('homePage.pricing.plans.month3.name'),
                price: t('homePage.pricing.plans.month3.price'),
                discount: t('homePage.pricing.plans.month3.discount'),
                features: t('homePage.pricing.plans.month3.features', { returnObjects: true }),
            },
            year1: {
                name: t('homePage.pricing.plans.year1.name'),
                price: t('homePage.pricing.plans.year1.price'),
                discount: t('homePage.pricing.plans.year1.discount'),
                features: t('homePage.pricing.plans.year1.features', { returnObjects: true }),
            }
        },
        button: t('homePage.pricing.button'),
    }

    const plans = [
        {
            id: 1,
            name: pricingData.plans.month1.name,
            price: pricingData.plans.month1.price,
            features: pricingData.plans.month1.features as string[],
        },
        {
            id: 2,
            name: pricingData.plans.month3.name,
            price: pricingData.plans.month3.price,
            discount: pricingData.plans.month3.discount,
            features: pricingData.plans.month3.features as string[],
            isPopular: true
        },
        {
            id: 3,
            name: pricingData.plans.year1.name,
            price: pricingData.plans.year1.price,
            discount: pricingData.plans.year1.discount,
            features: pricingData.plans.year1.features as string[],
        }
    ]

    const plansToShow = plans

    return (
        <Container size="lg" py="xl" id="pricing">
            {/* Заголовок и подзаголовок */}
            <Stack gap="xs" mb="xl">
                <Title order={1} c="white" ta="left" fz={{ base: 'h2', md: 'h1' }}>
                    {pricingData.title}
                </Title>
                <Text c="gray.4" ta="left" size="lg" maw={800}>
                    {pricingData.subtitle}
                </Text>
            </Stack>

            {/* Карточки тарифов */}
            <SimpleGrid cols={{ base: 1, md: 3 }} spacing="lg">
                {plansToShow.map((plan) => (
                    <Card
                        key={plan.id}
                        radius="lg"
                        padding="xl"
                        withBorder
                        bg="dark.6"
                        style={{
                            position: 'relative',
                            transition: 'transform 0.2s ease'
                        }}
                    >
                        <Stack gap="md" h="100%" justify="space-between">
                            {/* Заголовок тарифа */}
                            <div>
                                <div>
                                    <Title order={2} c="white" ta="left">
                                        {plan.name}
                                    </Title>
                                </div>

                                {/* Цена */}

                                <Text c="white" size="xl" ta="left" fw={100}>
                                    {plan.price}
                                </Text>


                                <Divider my="md" />

                                {/* Список возможностей */}
                                <Stack gap="xs">
                                    {plan.features.map((feature: string, index: number) => (
                                        <Group key={index} gap="xs" wrap="nowrap">
                                            <Text c="dark.2" size="sm" fw={500}>
                                                ⬤
                                            </Text>
                                            <Text c="white" size="sm" style={{ flex: 1 }}>
                                                {feature}
                                            </Text>
                                        </Group>
                                    ))}
                                </Stack>
                            </div>

                            {/* Кнопка */}
                            <Button
                                variant="white"
                                size="md"
                                radius="lg"
                                fullWidth
                                mt="md"
                                ml="0"
                                style={{
                                    backgroundColor: 'white',
                                    color: 'var(--mantine-color-dark-7)',
                                    fontWeight: 400,
                                }}
                                styles={{
                                    root: {
                                        '&:hover': {
                                            backgroundColor: 'var(--mantine-color-gray-1)',
                                            color: 'var(--mantine-color-dark-7)',
                                        }
                                    }
                                }}
                            >
                                {pricingData.button}
                            </Button>
                    </Stack>
                    </Card>
                ))}
        </SimpleGrid>
        </Container >
    )
}

export default Pricing
