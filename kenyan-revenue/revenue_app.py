import streamlit as st
import pandas as pd
import plotly.express as px

# Streamlit app layout
st.title("Kenya National Revenue Expenditure")

# File uploader
uploaded_file = st.file_uploader(
    "Upload CSV or Excel file", type=["csv", "xlsx"])

if uploaded_file is not None:
    # Read data
    if uploaded_file.name.endswith(".csv"):
        df = pd.read_csv(uploaded_file)
    else:
        df = pd.read_excel(uploaded_file)

    # Display data
    st.write("### Uploaded Data")
    st.dataframe(df)

    # Ensure necessary columns exist
    required_columns = ["category", "sub_category", "amount"]
    if all(col in df.columns for col in required_columns):
        # Dropdown filter
        category = st.selectbox("Select Category", df["category"].unique())
        filtered_df = df[df["category"] == category]

        # Visualization
        fig = px.bar(filtered_df, x="sub_category", y="amount",
                     title=f"{category} Allocation")
        st.plotly_chart(fig)
    else:
        st.error(
            "Uploaded file must contain columns: category, sub_category, and amount.")

st.write("Data Source: User Uploaded File")
