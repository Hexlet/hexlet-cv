import { Center, Container, Title } from '@mantine/core';
import { useTranslation } from 'react-i18next';
import { CommercialProjects } from '@widgets/commercial-projects';

export default function Home() {
  const { t } = useTranslation();

  return (
    <>
      <Center h="100vh">
        <Container 
          ta='center'
          c='orange'
        >
          <Title>
            {t('homePage.greetings')} Я - главная страница!
          </Title>
        </Container>
      </Center>
      <CommercialProjects/>
    </>
  );
}
