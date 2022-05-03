import React, { useState, useEffect } from "react";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
  } from 'chart.js';
import { useWebSocket, ReadyState } from "react-use-websocket/dist/lib/use-websocket";
import { Line } from "react-chartjs-2";

import "./StatsWidget.css";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
  );

  const CHART_COLORS = {
    red: 'rgb(255, 99, 132)',
    orange: 'rgb(255, 159, 64)',
    yellow: 'rgb(255, 205, 86)',
    green: 'rgb(75, 192, 192)',
    blue: 'rgb(54, 162, 235)',
    purple: 'rgb(153, 102, 255)',
    grey: 'rgb(201, 203, 207)'
  };

const TICK_COUNT = 50;
const labels = [];
for (let i = 0; i <= TICK_COUNT; ++i) {
    labels.push(i.toString());
}

export default function StatsWidget() {
    const protocol = window.location.protocol === "https:" ? "wss" : "wss"
    const host = window.location.host;
    const port = window.location.port;

	const { sendMessage, lastMessage, readyState } = useWebSocket(protocol + "://"+ host +":" + port + "/gm");
	const [graphHistory, setGraphHistory] = useState([]);

	useEffect(() => {
		if (lastMessage !== null) {
			const data = JSON.parse(lastMessage.data);
            var newGraph = graphHistory;
            newGraph.push({ time: new Date().getTime(), tick: data.object});
            if(newGraph.length > TICK_COUNT) {
                newGraph.splice(0, 1);
            }
			setGraphHistory(newGraph);
		}
	}, [lastMessage, setGraphHistory]);

	/*const connectionStatus = {
		[ReadyState.CONNECTING]: "Connecting",
		[ReadyState.OPEN]: "Open",
		[ReadyState.CLOSING]: "Closing",
		[ReadyState.CLOSED]: "Closed",
		[ReadyState.UNINSTANTIATED]: "Uninstantiated",
	}[readyState];*/

	return (
		<div className="graph">
			<p> Server Performance (Ticks) </p>
			<Line
				datasetIdKey="1"
				data={{
                    labels,
                    datasets: [
                        {
                            label: '',
                            data: graphHistory.map((l) => l.tick),
                            borderColor: CHART_COLORS.red,
                            fill: false,
                            cubicInterpolationMode: 'monotone',
                            tension: 0.4
                        }
                    ]
                }}
                options={{
                    y: {
                        suggestedMin: 995,
                        suggestedMax: 1005
                    }
                }}/>
		</div>
	);
}
