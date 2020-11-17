import React, { useState, useContext } from 'react';
import { Context } from "../context"
import ListFilter from './listFilter';
import ListContainer from './listContainer';
import "./sidePanel.css";
import { useHistory } from 'react-router-dom';
import AddList from './addList'


export default function SidePanel() {
    const [user] = useContext(Context)
    const [show , setShow] = useState(false);
    const history = useHistory();

    const signOut = () => {
        history.push({
            pathname: '/',
        });
    }

    const handleAddListModal = () => {
        setShow(false);
    }

    return (
        <div className="side_panel">
            <div>
                <h1 className="user_name">{user.first_name}'s Lists</h1>
                <button className="add_list_button" onClick={() => setShow(true)}>Add List</button>
                <AddList show={show} close={handleAddListModal}/>
                <ListFilter />
                <ListContainer />
                <button className="sign_out_button" onClick={signOut}>Sign Out</button>
            </div>
        </div>
    );
}

