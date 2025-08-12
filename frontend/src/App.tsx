import { BrowserRouter as Router, Route, Routes } from 'react-router';
import { MantineProvider } from '@mantine/core';
import '@mantine/core/styles.css';
import Home from './components/Home';
import Login from './components/Login';

function App() {
  return (
    <MantineProvider>
      <>
        <Router>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
          </Routes>
        </Router>
      </>
    </MantineProvider>
   
  )
}

export default App;
