import { useEffect, useState } from "react";
import axios from "axios";
import { PieChart, Pie, Cell, Tooltip, Legend } from "recharts";

const RevenueChart = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/api/revenue")
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => console.error("Error fetching data:", error));
  }, []);

  const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042"];

  return (
    <PieChart width={400} height={300}>
      <Pie
        data={data}
        cx={200}
        cy={150}
        outerRadius={100}
        fill="#8884d8"
        dataKey="amount"
        label
      >
        {data.map((entry, index) => (
          <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
        ))}
      </Pie>
      <Tooltip />
      <Legend />
    </PieChart>
  );
};

export default RevenueChart;
