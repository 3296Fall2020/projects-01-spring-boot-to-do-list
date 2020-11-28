import React from 'react'

export default function Item({item, click}){

    const handleClick = () => {
        click(item);
    }

    return(
        <div onClick={handleClick}>
           {item.task_name}
        </div>
    )
}