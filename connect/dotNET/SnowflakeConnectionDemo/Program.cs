using System;
using System.Data;
using Snowflake.Data.Client;
using Snowflake.Data.Core;


namespace SnowflakeConnectionDemo
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Begin Snowflake|dotnet Connection Demo!");
			
            string account = Environment.GetEnvironmentVariable("SF_ACCOUNT");
            string user = Environment.GetEnvironmentVariable("SF_USER");
            string pswd = Environment.GetEnvironmentVariable("SF_PASSWORD");
            string db = Environment.GetEnvironmentVariable("SF_DB");
            string schema = Environment.GetEnvironmentVariable("SF_SCHEMA");
            string wh = Environment.GetEnvironmentVariable("SF_WAREHOUSE");
            String connString = $"ACCOUNT={account};USER={user};PASSWORD={pswd};DB={db};SCHEMA={schema};WAREHOUSE={wh}";

//DB={db};WAREHOUSE={wh}";

			using (IDbConnection conn = new SnowflakeDbConnection())
            {
				conn.ConnectionString = connString;
				conn.Open();

				IDbCommand cmd = conn.CreateCommand();
				cmd.CommandText = "select n_nationkey, n_name from nation";
				String nationKey;
				String nationName;
                IDataReader reader = cmd.ExecuteReader();
				while(reader.Read())
				{
				
					nationKey = reader.GetString(0);
					nationName = reader.GetString(1);
					Console.WriteLine($"{nationKey} - {nationName}");
				}
				conn.Close();
            }	
            Console.WriteLine("End Snowflake|dotnet Connection Demo!");
        }
    }
}
