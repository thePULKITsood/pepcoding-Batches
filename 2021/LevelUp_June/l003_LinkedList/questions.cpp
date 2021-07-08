#include <iostream>
using namespace std;

class ListNode
{
public:
    int val = 0;
    ListNode *next = nullptr;

    ListNode(int val)
    {
        this->val = val;
    }
};

ListNode *midNode(ListNode *head)
{
    if (head == nullptr || head->next == nullptr)
        return head;

    ListNode *slow = head, *fast = head;
    while (fast->next != nullptr && fast->next->next != nullptr)
    {
        fast = fast->next->next;
        slow = slow->next;
    }
    return slow;
}

ListNode *reverse(ListNode *head)
{
    if (head == nullptr || head->next == nullptr)
        return head;

    ListNode *prev = nullptr, *curr = head;
    while (curr != nullptr)
    {
        ListNode *forw = curr->next; // backup

        curr->next = prev; // link

        prev = curr; // move
        curr = forw;
    }

    return prev;
}

void fold(ListNode *head)
{
    if (head == nullptr || head->next == nullptr)
        return;

    ListNode *mid = midNode(head);
    ListNode *nhead = mid->next;
    mid->next = nullptr;
    nhead = reverse(nhead);

    ListNode *c1 = head, *c2 = nhead;

    while (c2 != nullptr)
    {
        ListNode *f1 = c1->next, *f2 = c2->next;

        c1->next = c2;
        c2->next = f1;

        c1 = f1;
        c2 = f2;
    }
}

ListNode *mergeTwoLists(ListNode *l1, ListNode *l2)
{
    if (l1 == nullptr || l2 == nullptr)
        return l1 != nullptr ? l1 : l2;

    ListNode *dummy = new ListNode(-1);
    ListNode *prev = dummy, *c1 = l1, *c2 = l2;

    while (c1 != nullptr && c2 != nullptr)
    {
        if (c1->val <= c2->val)
        {
            prev->next = c1;
            c1 = c1->next;
        }
        else
        {
            prev->next = c2;
            c2 = c2->next;
        }
        prev = prev->next;
    }

    prev->next = c1 != nullptr ? c1 : c2;
    ListNode *head = dummy->next;
    dummy->next = nullptr;
    delete dummy;
    return head;
}

ListNode *segregateEvenOdd(ListNode *head)
{
    if (head == nullptr || head->next == nullptr)
        return head;

    ListNode *even = new ListNode(-1), *odd = new ListNode(-1);
    ListNode *ep = even, *op = odd, *curr = head;

    while (curr != nullptr)
    {
        if (curr->val % 2 != 0)
        {
            op->next = curr;
            op = op->next;
        }
        else
        {
            ep->next = curr;
            ep = ep->next;
        }
        curr = curr->next;
    }

    ep->next = nullptr, op->next = nullptr;
    ep->next = odd->next;

    return even->next;
}

int length(ListNode *head)
{
    int len = 0;
    while (head != nullptr)
    {
        head = head->next;
        len++;
    }
    return len;
}

ListNode *th = nullptr, *tt = nullptr;

void addFirstNode(ListNode *node)
{
    if (th == nullptr)
        th = tt = node;
    else
    {
        node->next = th;
        th = node;
    }
}

ListNode *reverseInKGroup(ListNode *head, int k)
{
    if (head == nullptr || head->next == nullptr || k <= 1)
        return head;

    int len = length(head);
    ListNode *curr = head, *oh = nullptr, *ot = nullptr;
    while (curr != nullptr && len >= k)
    {
        int tempK = k;
        while (tempK-- > 0)
        {
            ListNode *forw = curr->next;
            curr->next = nullptr;
            addFirstNode(curr);
            curr = forw;
        }

        if (oh == nullptr)
        {
            oh = th;
            ot = tt;
        }
        else
        {
            ot->next = th;
            ot = tt;
        }

        th = tt = nullptr;
        len -= k;
    }

    ot->next = curr;

    return oh;
}
