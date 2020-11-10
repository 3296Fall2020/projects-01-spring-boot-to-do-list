import "./homePageStyle.css";
import {Link} from 'react-router-dom';


export default function Home(){
    return(
        <div className="home">
           <h1>SPRING BOOT TO DO LIST</h1>
           <h3>Making Project Organization Spring to Life!</h3>
           <img src='./images/projectManagement.jpg' alt="home page icon"/>
           <div>
           <button ><Link to="/signIn" className="link">GET STARTED NOW</Link></button>
           </div>
        </div>
    )
}