import React, { Component, useState, useEffect } from "react";
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from "chart.js";
import { Line } from "react-chartjs-2";

import "./StatsWidget.css";
import WebsocketContext, { useWebsocket } from "../../Context/WebsocketProvider";
import { toHaveStyle } from "@testing-library/jest-dom/dist/matchers";

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement);

const CHART_COLORS = {
	red: "rgb(255, 99, 132)",
	orange: "rgb(255, 159, 64)",
	yellow: "rgb(255, 205, 86)",
	green: "rgb(75, 192, 192)",
	blue: "rgb(54, 162, 235)",
	purple: "rgb(153, 102, 255)",
	grey: "rgb(201, 203, 207)",
};

const TICK_COUNT = 25;
const labels = [];
for (let i = 0; i <= TICK_COUNT; ++i) {
	labels.push(i.toString());
}

export default class StatsWidget extends Component {
	constructor(props) {
		super(props);

		this.state = {
			graphHistory: [],
		};
	}

	componentDidUpdate(lastProps) {
		if (this.props.data !== this.props.ignoreData) {
			if (this.props.serverUptime !== lastProps.serverUptime) {
				var newGraph = this.state.graphHistory;
				console.log(this.props);
				newGraph.push({ tick: this.props.data });

				if (newGraph.length > TICK_COUNT) {
					newGraph.splice(0, 1);
				}

				this.setState({
					graphHistory: newGraph,
				});
			}
		}
	}

	render() {
		return (
			<div className="graph">
				<p> { this.props.title } </p>
				<p> {JSON.stringify(this.state.data)} </p>
				<Line
					datasetIdKey="1"
					data={{
						labels: this.state.graphHistory.map((gh, i) => this.state.graphHistory.length - i),
						datasets: [
							{
								label: "",
								data: this.state.graphHistory.map((gh) => gh.tick),
								borderColor: CHART_COLORS.red,
								fill: false,
								cubicInterpolationMode: "monotone",
								tension: 0.4,
							},
						],
					}}
					options={{
						y: {
							suggestedMin: this.props.suggestedYMin,
							suggestedMax: this.props.suggestedYMax,
						},
					}}
				/>
			</div>
		);
	}
}

/*export default function StatsWidget() {
	const [graphHistory, setGraphHistory] = useState([]);
    const [lastMessage, connectionStatus, sendMessage] = useWebsocket();

	useEffect(() => {
        console.log(lastMessage())
		if (lastMessage() !== null) {
			const { data } = JSON.parse(lastMessage().data);
            var newGraph = graphHistory;
            newGraph.push({ time: new Date().getTime(), tick: data.tickTimeElapsed });
            if(newGraph.length > TICK_COUNT) {
                newGraph.splice(0, 1);
            }
			setGraphHistory(newGraph);
		}
	}, [graphHistory, lastMessage, setGraphHistory]);

	const connectionStatus = {
		[ReadyState.CONNECTING]: "Connecting",
		[ReadyState.OPEN]: "Open",
		[ReadyState.CLOSING]: "Closing",
		[ReadyState.CLOSED]: "Closed",
		[ReadyState.UNINSTANTIATED]: "Uninstantiated",
	}[readyState];

	return (
		
	);
}*/
