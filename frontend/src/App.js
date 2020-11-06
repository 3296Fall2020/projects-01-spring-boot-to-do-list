import './App.css';
import SignIn from './components/userValidationComponents/signIn';
import SignUp from './components/userValidationComponents/signUp';
import Home from './components/homePage/homePage';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import ToDoList from './components/todoList';

function App() {
  return (
    <Router>
    <div className="App">
      <Switch>
      <Route exact path='/' component = {Home} />
        <Route path='/signIn' component = {SignIn} />
        <Route path='/signUp' component = {SignUp} />
        <Route path='/todoList' component = {ToDoList} />
      </Switch>
    </div>
    </Router>
  );
}


export default App;
