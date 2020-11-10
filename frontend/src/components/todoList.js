import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import {useHistory} from 'react-router-dom';


export default function ToDoList() {
  const location = useLocation();
  const [user, setUser] = useState({});
  const history = useHistory();
 
  useEffect(() => {
    console.log(location.state);
     if(location.state === undefined){
       alert("invalid user");
       history.push('/');
     } else {
      alert("User is: " + location.state.email);
      setUser({'email':location.state.email, 'password': location.state.password});
     }
  }, [location]);


 
    return (
      <div>
          WELCOME TO THE SPRING BOOT TO DO LIST. YOU ARE SIGNED IN AND HAVE ACCESS TO THE SPRING BOOT TO DO LIST
      </div>
    );
  }
  
  