import React, { Component } from 'react'
import Sidepanel from './Components/Sidepanel'

import './Dashboard.css'

export default class Dashboard extends Component {
  render() {
    return (
        <div className='dashboard'> 
            <Sidepanel />
        </div>
    )
  }
}
