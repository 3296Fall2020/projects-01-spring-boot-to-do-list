import './App.css';
import SignIn from './components/userValidationComponents/signIn';
import SignUp from './components/userValidationComponents/signUp';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import ToDoList from './components/todoList';

function App() {
  return (
    <Router>
    <div className="App">
      <Switch>
        <Route path='/signIn' component = {SignIn} />
        <Route path='/signUp' component = {SignUp} />
        <Route path='/todoList' component = {ToDoList} />
      </Switch>
    </div>
    </Router>
  );
}


export default App;
