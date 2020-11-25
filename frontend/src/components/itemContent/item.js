import React from 'react'
import { useState} from 'react'

export default function Item({item, click}){

    const handleClick = () => {
        click(item);
    }

    return(
        <div className="item_container" onClick={handleClick}>
           <h3>{item.task_name}</h3>
        </div>
    )
}