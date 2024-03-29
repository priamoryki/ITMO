module HW1.T1 where

import GHC.Natural

data Day = Monday | Tuesday | Wednesday | Thursday | Friday | Saturday | Sunday deriving Show

-- | Returns the day that follows the day of the week given as input.
nextDay :: Day -> Day
nextDay Monday    = Tuesday
nextDay Tuesday   = Wednesday
nextDay Wednesday = Thursday
nextDay Thursday  = Friday
nextDay Friday    = Saturday
nextDay Saturday  = Sunday
nextDay Sunday    = Monday

-- | Returns the day of the week after a given number of days has passed.
afterDays :: Natural -> Day -> Day
afterDays 0 day = day
afterDays n day = afterDays (n - 1) (nextDay day)

-- | Checks if the day is on the weekend.
isWeekend :: Day -> Bool
isWeekend Saturday = True
isWeekend Sunday   = True
isWeekend _        = False

-- | Computes the number of days until Friday.
daysToParty :: Day -> Natural
daysToParty Friday = 0
daysToParty day    = daysToParty (nextDay day) + 1
