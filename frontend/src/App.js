import {Route, Routes} from "react-router";
import {BrowserRouter as Router} from "react-router-dom";
import './App.css';
import HeaderComponent from './components/HeaderComponent';
import UploadFileComponent from './components/UploadFileComponent';

function App() {
  return (
    <Router>
      <HeaderComponent />
      <div className='container'>
        <Routes>
          <Route path='/' element={<UploadFileComponent />}></Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
