import React, { Component } from 'react'
import gclogo from "../img/grasscutter-icon.png"
import NavigationButton from './NavigationButton'
import SimpleButton from './SimpleButton'
import { faArrowLeft, faCogs, faGamepad, faHome, faNetworkWired } from '@fortawesome/free-solid-svg-icons'
import './Sidepanel.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

export default class Sidepanel extends Component {
  render() {
    return (
      <div className='sidepanel'>
        <div className='logo'>
            <img src={gclogo} alt="grasscutter logo" />
            <p> GCGM Dashboard </p>
        </div>
        <hr />
        <div className='navigation'>
            <NavigationButton name="Home" icon={faHome} link="/" />
            <NavigationButton name="Dispatch Server" icon={faNetworkWired} link="/" />
            <NavigationButton name="Game Server" icon={faGamepad} link="/" />
            <NavigationButton name="Settings" icon={faCogs} link="/" />
        </div>
        <SimpleButton id="collapse" icon={faArrowLeft} />
      </div>
    )
  }
}
