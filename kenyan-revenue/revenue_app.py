import streamlit as st
import pandas as pd
import plotly.express as px

# Streamlit app layout
st.title("Kenya National Revenue Expenditure")

# File uploader
uploaded_file = st.file_uploader("Upload CSV or Excel file", type=[
                                 "csv", "xlsx"], accept_multiple_files=False)

if uploaded_file is not None:
    # Read data
    if uploaded_file.name.endswith(".csv"):
        df = pd.read_csv(uploaded_file)
    else:
        df = pd.read_excel(uploaded_file)

    # Data validation
    required_columns = ["category", "sub_category", "amount"]
    if not all(col in df.columns for col in required_columns):
        st.error(
            "Uploaded file must contain columns: category, sub_category, and amount.")
    else:
        # Display data
        st.write("### Uploaded Data")
        st.dataframe(df)

        # Summary statistics
        total_amount = df["amount"].sum()
        st.metric(label="Total Budget Allocations",
                  value=f"Ksh {total_amount:,.2f}")

        # Dropdown filter
        category = st.selectbox("Select Category", df["category"].unique())
        filtered_df = df[df["category"] == category]

        # Visualization - Bar Chart
        fig_bar = px.bar(filtered_df, x="sub_category", y="amount",
                         title=f"{category} Allocation", color="sub_category")
        st.plotly_chart(fig_bar)

        # Visualization - Pie Chart
        fig_pie = px.pie(filtered_df, names="sub_category",
                         values="amount", title=f"{category} Distribution")
        st.plotly_chart(fig_pie)

        # Download processed data
        csv = df.to_csv(index=False).encode('utf-8')
        st.download_button(label="Download Processed Data", data=csv,
                           file_name="processed_revenue_data.csv", mime="text/csv")

st.write("Data Source: User Uploaded File")
