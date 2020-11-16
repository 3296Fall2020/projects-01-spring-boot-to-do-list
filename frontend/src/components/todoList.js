import React from 'react';
import { ContextProvider } from "./context"
import ListContent from './listContent/listContents';
import SidePanel from './sidePanelContainer/sidePanel'
import './todoList.css'



export default function ToDoList() {



  return (
    <div className="container">
      <ContextProvider>
        <SidePanel/>
        <ListContent/>
      </ContextProvider>
    </div>
  );
}

