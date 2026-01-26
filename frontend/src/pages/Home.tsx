import {
  Container,
  getGradient,
  useMantineTheme,
  Stack
} from '@mantine/core'
import { Footer } from '@widgets/Footer'
import { Header } from '@widgets/Header'
import { AboutUs } from '@widgets/about-us'
import { WhoWeAre } from '@widgets/who-we-are'
import { CommercialProjects } from '@widgets/commercial-projects'
import { MarketAnalytics } from '@widgets/market-analytics'
import { TrainingPrograms, type TrainingCardDto } from '@widgets/training-programs'
import { PerformanceReview, type PerformanceCardDto } from '@widgets/performance-review'
import { Communities } from '@widgets/communities'
// import { Link } from '@inertiajs/react'

type PageSection = {
  id: number
  pageKey: string
  sectionKey: string
  title: string
  content: string
  active: boolean
  createdAt: string
  updatedAt: string
}

type IndexProps = {
  trainingPrograms: TrainingCardDto []
  performanceReview: PerformanceCardDto[]
  pageSections: Readonly<PageSection[]>
}

const Index: React.FC<IndexProps> = (props) => {
  const { trainingPrograms, performanceReview, pageSections } = props

  const theme = useMantineTheme()
  console.log(`Page sections:`, pageSections)

  return (
    <Stack
      bg={getGradient({
        deg: 135,
        from: 'black',
        to: '#00031a',
      }, theme)}
      justify="space-between"
    >
      <Header />
      <Container size="xl">
        <AboutUs />
        <WhoWeAre />
        <CommercialProjects />
        <MarketAnalytics />
        <TrainingPrograms trainingPrograms={trainingPrograms} />
        <PerformanceReview performanceReview={performanceReview} />
        <Communities />
        {/* <Link href="/account">Personal Cabinet</Link> */}
      </Container>
      <Footer />
    </Stack>
  )
}

export default Index
