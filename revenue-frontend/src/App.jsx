import React from "react";
import RevenueChart from "./RevenueChart";
import { useState, useEffect } from "react";
import axios from "axios";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer } from "recharts";
import "./App.css";

function App() {
  const [revenueData, setRevenueData] = useState([]);

  useEffect(() => {
    axios.post("http://localhost:8080/api/revenue/all")
      .then((response) => {
        console.log("Fetched revenue data:", response.data);
        setRevenueData(response.data);
      })
      .catch((error) => {
        console.error("Error fetching revenue data:", error);
      });
  }, []);
  

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-4">
      <h1 className="text-3xl font-bold text-blue-600">Kenya National Revenue</h1>
      
      <div className="mt-8 w-full max-w-3xl">
        <ResponsiveContainer width="100%" height={300}>
          <BarChart data={revenueData}>
            <XAxis dataKey="category" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="amount" fill="#3182CE" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}

export default App;
