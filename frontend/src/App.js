import './App.css';
import SignIn from './components/userValidationComponents/signIn';
import SignUp from './components/userValidationComponents/signUp';
import Home from './components/homePage/homePage';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import ToDoList from './components/todoList';
import UpdateUser from './components/userValidationComponents/updateUser';

function App() {
  return (
    <Router>
    <div id="App">
      <Switch>
      <Route exact path='/' component = {Home} />
        <Route path='/signIn' component = {SignIn} />
        <Route path='/signUp' component = {SignUp} />
        <Route path='/todoList' component = {ToDoList} />
        <Route path='/updateUser' component = {UpdateUser} />
      </Switch>
    </div>
    </Router>
  );
}


export default App;
