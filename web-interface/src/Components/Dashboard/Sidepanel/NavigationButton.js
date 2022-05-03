import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

export default function NavigationButton(props) {
  return (
    <div className='nav-button'>
            <FontAwesomeIcon icon={props.icon} />
            <p> {props.name} </p>
        </div>
  )
}
