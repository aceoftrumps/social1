function RandomVariable(length)
	local res = ""
	for i = 1, length do
		res = res .. string.char(math.random(97, 122))
	end
	return res
end

request = function()
   path = "/user?fname=" .. RandomVariable(1) .. "&lname=" .. RandomVariable(1)
   wrk.headers["JSESSIONID	"] = "BCAC0AC01CAF7B9ECFF2A30D2F88EB7D"
   return wrk.format(nil, path)
end
