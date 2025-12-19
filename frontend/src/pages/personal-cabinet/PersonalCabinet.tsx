import { Body } from './components/Body';
import { Header } from './components/Header';
import { MenuItem, Navbar } from './components/Navbar';

type TProps = {
  data: MenuItem[];
};

const PersonalCabinet: React.FC<TProps> = (props) => {
  const { data } = props;

  return (
    <>
      <Header />
      <Navbar data={data} />
      <Body />
      {/* TODO: Переиспользовать виджет Footer. */}
    </>
  );
};

export default PersonalCabinet;
