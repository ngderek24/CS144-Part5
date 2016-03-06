Q1: (4) -> (5)
	(5) -> (6)

Q2: We ensured that the item was purchased exactly at the Buy_Price by obtaining the Buy_Price value directly from the server when we retrieved the item information and storing it in the HTTP session for later accesses. Using HTTP session is also a way of defending against client state manipulation. The user has no way of changing the value of the Buy_Price in the session.