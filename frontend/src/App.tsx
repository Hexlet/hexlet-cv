import { MantineProvider } from '@mantine/core';
import '@mantine/core/styles.css';
import Home from './Pages/Home';

function App() {
  return (
    <MantineProvider>
      <Home />
    </MantineProvider>
  );
}

export default App;