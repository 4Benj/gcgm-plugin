import React, { Component } from "react";
import Sidepanel from "./Components/Dashboard/Sidepanel/Sidepanel";
import StatsWidget from "./Components/Dashboard/StatsWidget";
import WebsocketContext, { useWebsocket } from "./Context/WebsocketProvider";

import "./Dashboard.css";
import { bytesToMegabytes } from "./util/util";

export default class Dashboard extends Component {
	static contextType = WebsocketContext;

	constructor(props) {
		super(props);

		this.state = {
			lastMessage: {
				serverUptime: 0,
			},
		};
	}

	componentDidUpdate() {
		if (typeof this.context.lastMessage().data !== "undefined") {
			if (this.context.lastMessage().data.serverUptime !== this.state.lastMessage.serverUptime) {
				//console.log(this.context.lastMessage().data);
				this.setState({
					lastMessage: this.context.lastMessage().data,
				});
			}
		}
	}

	render() {
		return (
			<div className="dashboard">
				<Sidepanel />
				<div className="content">
					<StatsWidget title="Server Performance (Ticks)" serverUptime={this.state.lastMessage.serverUptime} data={typeof this.state.lastMessage.tickTimeElapsed !== "undefined" ? this.state.lastMessage.tickTimeElapsed : -1} ignoreData={-1} suggestedYMin={995} suggestedYMax={1005} />
					<StatsWidget title="Server Performance (RAM)" serverUptime={this.state.lastMessage.serverUptime} data={typeof this.state.lastMessage.getFreeMemory !== "undefined" ? bytesToMegabytes(this.state.lastMessage.getAllocatedMemory - this.state.lastMessage.getFreeMemory, 2) : -1} ignoreData={-1} suggestedYMin={0} suggestedYMax={bytesToMegabytes(this.state.lastMessage.getAllocatedMemory)} />
					<StatsWidget title="Online Players" serverUptime={this.state.lastMessage.serverUptime} data={typeof this.state.lastMessage.playerCount !== "undefined" ? this.state.lastMessage.playerCount : -1} ignoreData={-1} suggestedYMin={0} suggestedYMax={10} />

        </div>
			</div>
		);
	}
}
