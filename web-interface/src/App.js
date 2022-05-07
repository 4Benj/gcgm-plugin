import { WebsocketProvider } from './Context/WebsocketProvider';
import Dashboard from './Dashboard';

function App() {
  return (
    <WebsocketProvider>
      <Dashboard />
    </WebsocketProvider>
  );
}

export default App;
