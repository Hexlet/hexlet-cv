import {
    Container,
    Title,
    Text,
    Group,
    Stack,
    Divider,
    SimpleGrid,
} from '@mantine/core'
import { useTranslation } from 'react-i18next'
import { DsCard } from '@shared/uikit/DsCard/DsCard'
import { DsText } from '@shared/uikit/DsText/DsText'

type TProps = {
    pricingPlans?: Array<{
        id: number
        name: string
        price: string
        features: string[]
        isPopular?: boolean
        discount?: string
    }>
}

export const Pricing: React.FC<TProps> = () => {
    const { t } = useTranslation()

    // Получаем данные из переводов
    const pricingData = {
        title: t('homePage.pricing.title'),
        subtitle: t('homePage.pricing.subtitle'),
        button: t('homePage.pricing.button'),
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

    const handleCardClick = (planId: number) => {
        console.log(`Выбрана карточка ${planId}`)
        // Здесь будет логика обработки клика по карточке
    }

    return (
        <Container size="lg" py="xl" id="pricing">
            {/* Заголовок и подзаголовок */}
            <Stack gap="xs" mb="xl">
                <Title order={1} ta="left" fz={{ base: 'h2', md: 'h1' }}>
                    {pricingData.title}
                </Title>
                <DsText tone="secondary" size="lg" maw={800} ta="left">
                    {pricingData.subtitle}
                </DsText>
            </Stack>

            {/* Карточки тарифов */}
            <SimpleGrid cols={{ base: 1, md: 3 }} spacing="lg">
                {plans.map((plan) => (
                    <DsCard
                        key={plan.id}
                    >
                        <DsCard.Content>
                            <div>
                                <div>
                                    <Title order={2} ta="left">
                                        {plan.name}
                                    </Title>
                                </div>

                                {/* Цена */}
                                <Text size="xl" ta="left" fw={100}>
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
                                            <Text size="sm" style={{ flex: 1 }}>
                                                {feature}
                                            </Text>
                                        </Group>
                                    ))}
                                </Stack>
                            </div>
                        </DsCard.Content>
                        <DsCard.Action
                            label={pricingData.button}
                            onClick={() => handleCardClick(plan.id)}
                        />
                    </DsCard>
                ))}
            </SimpleGrid>
        </Container>
    )
}

export default Pricing
