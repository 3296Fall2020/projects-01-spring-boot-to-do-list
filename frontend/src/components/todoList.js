import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import {useHistory} from 'react-router-dom';


export default function ToDoList() {
  const location = useLocation();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const history = useHistory();
 
  useEffect(() => {
    console.log(location.state);
     if(location.state === undefined){
       alert("invalid user");
       history.push('/');
     } else {
      alert(location.state);
      setEmail(location.state.email);
      setPassword(location.state.password);
     }
  }, [location]);


 
    return (
      <div>
          WELCOME TO THE SPRING BOOT TO DO LIST. YOU ARE SIGNED IN AND HAVE ACCESS TO THIS SITE
      </div>
    );
  }
  
  