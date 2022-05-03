import React, { Component } from 'react'
import Sidepanel from './Components/Dashboard/Sidepanel/Sidepanel'
import StatsWidget from './Components/Dashboard/StatsWidget'

import './Dashboard.css'

export default class Dashboard extends Component {
  render() {
    return (
        <div className='dashboard'> 
            <Sidepanel />
            <StatsWidget />
        </div>
    )
  }
}
